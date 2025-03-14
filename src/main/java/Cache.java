import java.util.Arrays;
import java.util.LinkedList;

public class Cache {
    private LinkedList<CacheLine>[] lines;
    private int size;
    private int blockSize;
    private int sets;
    private int hits;
    private int misses;
    private int mapping;
    private String policy;
    private int totalAccesses;

    private MainMemory memory;
    private MappingStrategy mappingStrategy;
    private ReplacementPolicy replacementPolicy;

    //after tests maybe delete the memory
//    public Cache(int size, int mapping, String policy, int blockSize, MainMemory memory){
//
//        this.size=size;
//        this.blockSize=blockSize;
//        this.hits=0;
//        this.misses=0;
//        this.mapping=mapping;
//        this.policy=policy;
//        this.sets=size/mapping;
//        this.memory=memory;
//        lines = new LinkedList[sets];
//        for(int i=0; i<sets; i++){
//            lines[i]=new LinkedList<>();
//        }
//
//        if(mapping == 1){
//            this.mappingStrategy = new DirectMapping();
//        } else if (mapping == size) {
//            this.mappingStrategy = new FullyAssociative();
//        }else{
//            this.mappingStrategy = new SetAssociative(mapping);
//        }
//
//        switch (policy){
//            case "FIFO":
//                this.replacementPolicy = new FIFOPolicy();
//                break;
//            case "LRU":
//                this.replacementPolicy = new LRUPolicy();
//                break;
//            case "Random":
//                this.replacementPolicy = new RandomPolicy();
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown replacement policy: " + policy);
//        }
//    }

    public Cache(int size, int mapping, String policy, int blockSize, MainMemory memory) {
        if (size <= 0 || mapping <= 0 || blockSize <= 0) {
            throw new IllegalArgumentException("Cache size, mapping, and block size must be greater than zero.");
        }

        this.size = size;
        this.blockSize = blockSize;
        this.mapping = mapping;
        this.sets = size / mapping; // Sets calculation
        this.hits = 0;
        this.misses = 0;
        this.memory = memory;

        // Initialize cache lines
        lines = new LinkedList[sets];
        for (int i = 0; i < sets; i++) {
            lines[i] = new LinkedList<>();
        }

        // Mapping strategy
        if (mapping == 1) {
            this.mappingStrategy = new DirectMapping();
        } else if (mapping == size) {
            this.mappingStrategy = new FullyAssociative();
        } else {
            this.mappingStrategy = new SetAssociative(mapping);
        }

        // Replacement policy
        switch (policy) {
            case "FIFO" -> this.replacementPolicy = new FIFOPolicy();
            case "LRU" -> this.replacementPolicy = new LRUPolicy();
            case "Random" -> this.replacementPolicy = new RandomPolicy();
            default -> throw new IllegalArgumentException("Unknown replacement policy: " + policy);
        }
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getMisses() {
        return misses;
    }

    public void setMisses(int misses) {
        this.misses = misses;
    }

    public int getMapping() {
        return mapping;
    }

    public void setMapping(int mapping) {
        this.mapping = mapping;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getTotalAccesses() {
        return totalAccesses;
    }

    public double getHitRate() {
        if (totalAccesses > 0) {
            return (double) hits / totalAccesses * 100;
        } else {
            return 0.0;
        }
    }

    public double getMissRate() {
        if (totalAccesses > 0) {
            return (double) misses / totalAccesses * 100;
        } else {
            return 0.0;
        }
    }

    public void warmUpCache(int[] addresses) {
        for (int address : addresses) {
            read(address); // Use read to load addresses, incrementing accesses without affecting test statistics.
        }
        // Reset hit and miss counters if you want to isolate test results.
        this.hits = 0;
        this.misses = 0;
        this.totalAccesses = 0;
    }

    public int read(int address){
        totalAccesses++;

        //calculate index and tag from address
        int index = calculateIndex(address);
        int tag = calculateTag(address);

        //the set of lines for this index
        LinkedList<CacheLine> set=lines[index];

        //check if the tag is in the cache
        for(CacheLine line : set){
            if(line.isValid() && line.getTag() == tag){
                hits++;
                replacementPolicy.update(set, line);
                return line.getData()[0];
            }
        }

        misses++;

        //load data from memory
        int[] dataMem = loadFromMemory(address);

        //create a new cache line for storing data
        CacheLine newLine = new CacheLine(blockSize);
        newLine.setValid(true);
        newLine.setTag(tag);
        newLine.setData(dataMem);

        //check if set is full
        if(set.size() >= mapping){
            if (!set.isEmpty()) {
                CacheLine removed = replacementPolicy.selectLineToRemove(set);
                //write back if dirty
                if (removed.isDirty()) {
                    writeBackToMemory(removed, index);
                }
            }

        }

        set.add(newLine);
        replacementPolicy.update(set, newLine);
        return  dataMem[0];
    }

    private int calculateIndex(int address){
//        int index=(int)(Math.log(sets)/Math.log(2));
//        int offset=(int)(Math.log(blockSize)/Math.log(2));
//        return (address >> offset)&(sets-1);
        return mappingStrategy.calculateIndex(address, sets, blockSize);
    }

    private int calculateTag(int address){
//        int index=(int)(Math.log(sets)/Math.log(2));
//        int offset=(int)(Math.log(blockSize)/Math.log(2));
//        return address >> (index + offset);
        return mappingStrategy.calculateTag(address, sets, blockSize);
    }

    private int[] loadFromMemory(int address){
        return new int[blockSize];
    }

    public void write(int address, int data){
        totalAccesses++;

        int index = calculateIndex(address);
        int tag = calculateTag(address);

        LinkedList<CacheLine> set = lines[index];
        for(CacheLine line : set){
            if(line.isValid() && line.getTag() == tag){
                hits++;
                line.setData(new int[]{data});
                line.setDirty(true);
                //////////
                memory.write(address, data);
                /////////////
                replacementPolicy.update(set, line);
                return;
            }
        }

        misses++;

        ////////////
        int[] dataMem = loadFromMemory(address);
        dataMem[0] = data;
        ////////////

        //create a new line for write
        CacheLine newLine = new CacheLine(blockSize);
        newLine.setData(new int[]{data});
        newLine.setDirty(true);
        newLine.setTag(tag);
        newLine.setValid(true);

        if(set.size() >= mapping){
            if (!set.isEmpty()) {
                CacheLine removed = replacementPolicy.selectLineToRemove(set);
                //write back if dirty
                if(removed.isDirty()){
                    writeBackToMemory(removed, index);
                }
            }

        }

        set.add(newLine);
        //////////
        memory.write(address, data);
        //////////
        replacementPolicy.update(set, newLine);
    }

    private void writeBackToMemory(CacheLine removed, int index) {
        int index1 = (int) (Math.log(sets) / Math.log(2));
        int offset = (int)(Math.log(blockSize) / Math.log(2));
        int address = (removed.getTag() << (index1 + offset)) | (index << offset);
        memory.writeBlock(address, removed.getData());
    }

    public void displayStatistics() {
        System.out.println("=== Cache Statistics ===");
        System.out.println("Total Accesses: " + totalAccesses);
        System.out.println("Cache Hits: " + hits);
        System.out.println("Cache Misses: " + misses);
        System.out.printf("Hit Rate: %.2f%%\n", getHitRate());
        System.out.printf("Miss Rate: %.2f%%\n", getMissRate());
        System.out.println("========================");
    }

    public String getStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== Cache Statistics ===\n");
        stats.append("Total Accesses: ").append(getTotalAccesses()).append("\n");
        stats.append("Cache Hits: ").append(getHits()).append("\n");
        stats.append("Cache Misses: ").append(getMisses()).append("\n");
        stats.append(String.format("Hit Rate: %.2f%%\n", getHitRate()));
        stats.append(String.format("Miss Rate: %.2f%%\n", getMissRate()));
        stats.append("========================\n");

        return stats.toString();
    }
///////////
    public String[][] getCacheState() {
        String[][] state = new String[sets * mapping][5];
        int row = 0;

        for (int i = 0; i < sets; i++) {
            for (CacheLine line : lines[i]) {
                state[row][0] = String.valueOf(i); // Index
                state[row][1] = line.isValid() ? String.valueOf(line.getTag()) : "N/A"; // Tag
                state[row][2] = Arrays.toString(line.getData()); // Data
                state[row][3] = String.valueOf(line.isValid()); // Valid
                state[row][4] = String.valueOf(line.isDirty()); // Dirty
                row++;
            }
        }
        return state;
    }
/////////////
}
