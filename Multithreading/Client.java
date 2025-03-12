public class Client {
    public static void main(String Arg[]){
        hello h= new hello();
        Thread Th= new Thread(h);
        Th.start();
    }
}
