public class Crawler {
    //爬虫主类
    public static void main(String[] args){
        MyThread myThread = new MyThread();
        Thread []threads = new Thread[5];
        for (int i = 0 ; i < 5 ; i ++){
            threads[i] = new Thread(myThread);
            threads[i].start();
        }
    }
}
