public class MainMemory {
    private int[] memory;

    // Constructor to initialize memory with a specified size
    public MainMemory(int size){
        this.memory = new int[size];
        initializeMemory();
    }

    private void initializeMemory() {
        for (int i = 0; i < memory.length; i++) {
            memory[i] = i;
        }
    }

    /**
     * Load a block of data from memory starting at a given address.
     * Used when there’s a cache miss.
     * @param address Starting address in memory
     * @param blockSize Number of words in the block
     * @return Array representing the data block
     */
    public int[] loadBlock(int address, int blockSize) {
        int[] block = new int[blockSize];
        for(int i = 0; i < blockSize; i++){
            if(address + i < memory.length)
                block[i] = memory[address + i];
            else
                block[i] = -1;
        }

        return block;
    }

    /**
     * Write a block of data back to memory, starting at a given address.
     * Used when a dirty cache line is evicted.
     * @param address Starting address in memory
     * @param data Array containing the data to write
     */
    public void writeBlock(int address, int[] data) {
        for(int i = 0; i < data.length; i++){
            if(address + i < memory.length)
                memory[address + i] = data[i];
        }
    }

    /**
     * Read a single data element from memory.
     * @param address The address in memory to read from
     * @return The value at the given address
     */
    public int read(int address) {
        if(address < memory.length)
            return memory[address];
        else
            throw new ArrayIndexOutOfBoundsException("Address out of memory bounds.");
    }

    /**
     * Write a single data element to memory at a specific address.
     * @param address The address in memory to write to
     * @param value The value to write at the address
     */
    public void write(int address, int value) {
        if(address < memory.length)
            memory[address] = value;
        else
            throw new ArrayIndexOutOfBoundsException("Address out of memory bounds.");
    }
}
