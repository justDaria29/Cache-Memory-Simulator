public interface MappingStrategy {
    int calculateIndex(int address, int sets, int blockSize);
    int calculateTag(int address, int sets, int blockSize);

}
