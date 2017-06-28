package test;

import main.service.UserService;
import main.service.UserServiceImpl;

/**
 * Created by 71903 on 2017/5/14.
 */
public class MapperTest {
    //    private static UserMapper userMapper=null;
//    public static void setMapper(){
//        if (userMapper==null){
//            userMapper=new UserMapperImpl();
//        }
//    }
//    private static CardMapper cardMapper=null;
//    public static void setMapper(){
//        if (cardMapper==null){
//            cardMapper=new CardMapperImpl();
//        }
//    }
//    private static TransrecordMapper transrecordMapper=null;
//    public static void setMapper(){
//        if(transrecordMapper==null){
//            transrecordMapper=new TransrecordMapperImpl();
//        }
//    }
//    private static AreasMapper areasMapper=null;
//    public static void setMapper(){
//        if(areasMapper==null){
//            areasMapper=new AreasMapperImpl();
//        }
//    }
    public static void main(String[] args) {
//        String password="123qwe";
//        try {
//            System.out.print(getMessageDigset(password.getBytes("UTF8")));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        int i;
        UserService service=new UserServiceImpl();
        i=service.signIn();
        System.out.println(i);
        i=service.transactionRecord();
        System.out.println(i);
//        System.out.println(i);
//        service.signIn();
//        service.userCards();
//        service.userInfo();
//        Areas areas=new Areas();
//        List<Areas> areass=new LinkedList<Areas>();
//        areas.setParentid(110100);
//        MapperTest.setMapper();
//        areass=areasMapper.selectByCondition(areas);
//        for (Areas areas1:areass)
//            System.out.println(areas1);

//        Transrecord transrecord=new Transrecord();
//        List<Transrecord> transrecords=new LinkedList<Transrecord>();
//        transrecord.setId(1);
//        transrecord.setCardnumber("66201156381512313");
//        transrecord.setTransamount(new BigDecimal(200.32));
//        transrecord.setBalance(new BigDecimal(210.32));
//        transrecord.setCurrency("CNY");
//        transrecord.setTranskind(1);
//        MapperTest.setMapper();
//        transrecords=transrecordMapper.selectByCondition(transrecord);
//        for(Transrecord transrecord1:transrecords){
//            System.out.println(transrecord1);
//        }

//        Card card=new Card();
//        List<Card> cards=new LinkedList<Card>();
//        card.setId(2);
//        card.setCardnumber("66201156381517894");
//        card.setBalance(new BigDecimal(1200.24));
//        card.setCurrency("CNY");
//        card.setCardholderid(4);
//        MapperTest.setMapper();
//        cards=cardMapper.selectByCondition(card);
//        for(Card card1:cards)
//        System.out.println(card1);
//        User user=new User();
//        List<User> users=new LinkedList<User>();
//        user.setId(1);
//        user.setUsername("danbu");
//        user.setPassword("1234567");
//        user.setSalt("7654321");
//        user.setSocialsecid("330602199706300010");
//        user.setGender("男");
//        user.setRegprovince(3);
//        user.setRegcity(3);
//        user.setRegregion(1);
//        user.setAddress("HZNU111");
//        user.setMphone("15366676569");

//        MapperTest.setMapper();
//        user=userMapper.selectByPrimaryKey(1);
//        users=userMapper.selectByCondition(user);
//        for(User user1:users) {
//            System.out.println(user1);
//        }

//        Scanner scanner=new Scanner(System.in);
//        String string=scanner.nextLine();
//        System.out.println(string);
//        string=TransactSQLInjection(string);
//        System.out.println(TransactSQLInjection(string));

    }
}
