package xjt.proxy;

public interface IUserDao {
    default public void save() {
        System.out.println("保存数据");
    }
}
