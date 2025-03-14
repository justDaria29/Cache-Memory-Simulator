import java.util.LinkedList;
import java.util.Random;
public class RandomPolicy implements ReplacementPolicy{
    private final Random random;

    public RandomPolicy(){
        this.random = new Random();
    }

    @Override
    public CacheLine selectLineToRemove(LinkedList<CacheLine> set){
        int index = random.nextInt(set.size());
        return set.remove(index);
    }

    @Override
    public void update(LinkedList<CacheLine> set, CacheLine accessedLine) {}
}
