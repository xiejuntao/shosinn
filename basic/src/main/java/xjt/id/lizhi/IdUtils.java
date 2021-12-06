package xjt.id.lizhi;

public class IdUtils {
    /**
     * 从ID中解出对象类型
     *
     * @param id
     * @return
     */
    public static final int extractType(long id) {
        return (int) id & 0x7F;
    }
    public static boolean checkIsWetalkId(long id){
        int type = IdUtils.extractType(id);
        if(type==81){//81为wetalk的全局id
            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args) {
        GuidGenerator guidGenerator = new GuidGenerator(0);
        System.out.println(guidGenerator.genBannerId());
    }
}
