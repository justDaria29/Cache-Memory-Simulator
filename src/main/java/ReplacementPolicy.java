import java.util.LinkedList;

public interface ReplacementPolicy {
    CacheLine selectLineToRemove(LinkedList<CacheLine> set);
    void update(LinkedList<CacheLine> set, CacheLine accessedLine);
}
