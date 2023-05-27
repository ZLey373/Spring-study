import com.zhaolei.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Mytest {
    public static void main(String[] args) {
        ApplicationContext Context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        User user = (User) Context.getBean("u4");
        user.show();
    }
}
