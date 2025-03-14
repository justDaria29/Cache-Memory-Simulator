import java.util.LinkedList;

public class LRUPolicy implements ReplacementPolicy{

    @Override
    public CacheLine selectLineToRemove(LinkedList<CacheLine> set){
        return set.removeFirst();
    }

    @Override
    public void update (LinkedList<CacheLine> set, CacheLine accessedLine){
        set.remove(accessedLine);
        set.addLast(accessedLine);
    }
}
