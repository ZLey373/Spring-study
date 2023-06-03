import com.zhaolei.pojo.Student;
import com.zhaolei.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Student student = (Student) context.getBean("student");
        System.out.println(student.toString());
    }
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("Applicationcontext.xml");
        User user = context.getBean("user2", User.class);
        User user2 = context.getBean("user2", User.class);
        System.out.println(user.hashCode());
        System.out.println(user2.hashCode());
        System.out.println(user2==user);
    }
}


/*
* Student{name='赵磊',
*         address=Address{address='西安电子科技大学'},
*          books=[计算机组成原理, 操作系统, 计算机网络, 数据机构],
*          hobbies=[自驾游, 敲代码, 旅游],
*          card={学号=22031212xxx, 姓名=赵磊, 专业=计算机科学与技术},
*          games=[王者荣耀, 部落冲突],
*          ptr='null',
*          info={password=123, url=http://dhsje12.com, drive=123783210hdks, username=root}
* }
* */