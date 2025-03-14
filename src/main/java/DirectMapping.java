//Tag to represent the high-order bits of the memory address and uniquely identify the memory block.
// We determine the cache line to which the memory block is mapped based on the index bits,
// while the offset bits specify the position of the data within the line

import java.util.List;

public class DirectMapping implements MappingStrategy {
    @Override
    public int calculateIndex(int address, int sets, int blockSize){
        int offset = (int) (Math.log(blockSize) / Math.log(2));
        return (address >> offset) & (sets - 1);
    }

    @Override
    public int calculateTag(int address, int sets, int blocksize){
        int index = (int) (Math.log(sets) / Math.log(2));
        int offset = (int) (Math.log(blocksize) / Math.log(2));
        return address >> (offset + index);
    }


    public CacheLine findCacheLine(int address, int sets, int blockSize, List<CacheLine> cacheLines) {
        int index = calculateIndex(address, sets, blockSize); // Get the index of the cache line
        int tag = calculateTag(address, sets, blockSize);     // Get the tag to verify the block

        CacheLine cacheLine = cacheLines.get(index);

        // if the cache line is valid and the tag matches
        if (cacheLine.isValid() && cacheLine.getTag() == tag) {
            return cacheLine; // Cache hit: Return the corresponding cache line
        }

        // if the tag does not match or the line is not valid, return null (cache miss)
        return null;
    }

}
