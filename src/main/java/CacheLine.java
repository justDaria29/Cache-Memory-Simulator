public class CacheLine {
    private int tag;
    private boolean valid;
    private boolean dirty;
    private int[] data;

    public CacheLine(int blockSize){
        this.valid=false;
        this.dirty=false;
        this.data=new int[blockSize];
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    public void reset(){
        this.tag=-1;
        this.valid=false;
        this.dirty=false;
        this.data=new int[data.length];
    }
}
