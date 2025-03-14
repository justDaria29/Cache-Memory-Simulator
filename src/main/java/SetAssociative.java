import java.util.LinkedList;
import java.util.List;

public class SetAssociative implements MappingStrategy{
    private int n;
    public SetAssociative(int n){
        this.n = n;
    }

    @Override
    public int calculateIndex(int address, int sets, int blockSize){
        int offset = (int) (Math.log(blockSize) / Math.log(2));
        return (address >> offset) & (sets - 1);
    }

    @Override
    public int calculateTag(int address, int sets, int blockSize){
        int index = (int) (Math.log(sets) / Math.log(2));
        int offset = (int) (Math.log(blockSize) / Math.log(2));
        return address >> (offset + index);
    }


    public CacheLine findCacheLine(int address, int sets, int blockSize, List<LinkedList<CacheLine>> cacheSets) {

        int index = calculateIndex(address, sets, blockSize); // Identify the set
        int tag = calculateTag(address, sets, blockSize);     // Calculate the tag for comparison

        LinkedList<CacheLine> set = cacheSets.get(index);

        // search the set for a matching cache line
        for (CacheLine line : set) {
            if (line.isValid() && line.getTag() == tag) {
                return line;
            }
        }

        return null;
    }

}
