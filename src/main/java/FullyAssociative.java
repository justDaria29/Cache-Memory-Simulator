//Tag: Unique identifier to check if the data in a cache line is what we need.
//Index: Points to a specific set in the cache where the data may be stored.
//Offset: Indicates a specific byte within a block.

import java.util.List;

public class FullyAssociative implements MappingStrategy{
    @Override
    public int calculateIndex(int address, int sets, int blockSize){
        return 0;
    }

    @Override
    public int calculateTag(int address, int sets, int blockSize){
        int offset = (int) (Math.log(blockSize) / Math.log(2));
        return address >> offset;
    }

    public CacheLine findCacheLine(int address, int blockSize, List<CacheLine> cacheLines) {
        // calculate the tag for the given address
        int tag = calculateTag(address, cacheLines.size(), blockSize);

        // search the entire cache for a matching line
        for (CacheLine line : cacheLines) {
            if (line.isValid() && line.getTag() == tag) {
                return line;
            }
        }

        return null;
    }

}
