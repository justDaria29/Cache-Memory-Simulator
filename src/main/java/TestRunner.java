import java.util.Arrays;

public class TestRunner {
    private final StringBuilder output;

    public TestRunner() {
        output = new StringBuilder();
    }

    public String runAllTests() {
        output.setLength(0); // Clear previous results
        MainMemory memory = new MainMemory(64);

        output.append("=== Mapping Strategy Tests ===\n");
        testMappingStrategies();

        output.append("\n=== Replacement Policy Tests ===\n");
        testReplacementPolicies(memory);

        output.append("\n=== Cache Hits and Misses Tests ===\n");
        testCacheHitsAndMisses(memory);

        output.append("\n=== Cache Statistics Tracking Test ===\n");
        testCacheStatistics(memory);

        return output.toString();
    }

    private void testMappingStrategies() {
        int cacheSize = 16;
        int blockSize = 4;
        int sets = 4;
        int[] addresses = {0, 4, 8, 12, 16, 20, 24, 28, 32, 36};

        output.append("Testing Direct Mapped Strategy:\n");
        MappingStrategy directMapped = new DirectMapping();
        testMappingStrategy(directMapped, addresses, sets, blockSize);

        output.append("\nTesting Fully Associative Strategy:\n");
        MappingStrategy fullyAssociative = new FullyAssociative();
        testMappingStrategy(fullyAssociative, addresses, sets, blockSize);

        output.append("\nTesting Set Associative Strategy (2-way):\n");
        MappingStrategy setAssociative = new SetAssociative(2);
        testMappingStrategy(setAssociative, addresses, sets, blockSize);
    }

    private void testMappingStrategy(MappingStrategy strategy, int[] addresses, int sets, int blockSize) {
        for (int address : addresses) {
            int index = strategy.calculateIndex(address, sets, blockSize);
            int tag = strategy.calculateTag(address, sets, blockSize);
            output.append(String.format("Address: %d -> Index: %d, Tag: %d%n", address, index, tag));
        }
    }

    private void testReplacementPolicies(MainMemory memory) {
        output.append("Testing FIFO Replacement Policy:\n");
        Cache cacheFIFO = new Cache(16, 4, "FIFO", 4, memory);
        performReplacementTest(cacheFIFO);

        output.append("\nTesting LRU Replacement Policy:\n");
        Cache cacheLRU = new Cache(16, 4, "LRU", 4, memory);
        performReplacementTest(cacheLRU);

        output.append("\nTesting Random Replacement Policy:\n");
        Cache cacheRandom = new Cache(16, 4, "Random", 4, memory);
        performReplacementTest(cacheRandom);
    }

    private void performReplacementTest(Cache cache) {
        int baseAddress = 0;
        int numAccesses = 6;

        for (int i = 0; i < numAccesses; i++) {
            int address = baseAddress + i * cache.getBlockSize();
            cache.read(address);
            output.append("Accessing address ").append(address).append("\n");
        }

        output.append("Re-accessing addresses for potential hits:\n");
        for (int i = 0; i < numAccesses / 2; i++) {
            int address = baseAddress + i * cache.getBlockSize();
            cache.read(address);
            output.append("Re-accessing address ").append(address).append("\n");
        }

        output.append(cache.getStatistics()).append("\n");
    }

    private void testCacheHitsAndMisses(MainMemory memory) {
        Cache cache = new Cache(16, 4, "LRU", 4, memory);

        output.append("Testing cache hits and misses:\n");

        cache.read(0);
        cache.read(0);
        cache.read(4);

        output.append(cache.getStatistics()).append("\n");
    }

    private void testCacheStatistics(MainMemory memory) {
        Cache cache = new Cache(16, 4, "FIFO", 4, memory);

        cache.read(0);
        cache.read(4);
        cache.read(0);
        cache.write(8, 123);
        cache.write(4, 456);

        output.append(cache.getStatistics()).append("\n");
    }
}
