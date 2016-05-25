package test;


public class L1CacheMiss {
    private static final int RUNS = 10;
    private static final int DIMENSION_1 = 1024 * 1024;
    private static final int DIMENSION_2 = 6;

    private static long[][] longs;

    public static void main(String[] args) throws Exception {
        //Thread.sleep(10000);
        longs = new long[DIMENSION_1][];
        for (int i = 0; i < DIMENSION_1; i++) {
            longs[i] = new long[DIMENSION_2];
            for (int j = 0; j < DIMENSION_2; j++) {
                longs[i][j] = 0L;
            }
        }
        System.out.println("starting....");

        long sum = 0L;
        for (int r = 0; r < RUNS; r++) {

            final long start = System.nanoTime();

            //slow
            for (int j = 0; j < DIMENSION_2; j++) {
                for (int i = 0; i < DIMENSION_1; i++) {
                    sum += longs[i][j];
                }
            }

            //            //fast
            //            for (int i = 0; i < DIMENSION_1; i++) {
            //                for (int j = 0; j < DIMENSION_2; j++) {
            //                    sum += longs[i][j];
            //                }
            //            }

            System.out.println((System.nanoTime() - start) / 1000 / 1000);
        }

    }
}

/**
 * 64λϵͳ��Java�������ͷ�̶�ռ16�ֽڣ�δ֤ʵ������long����ռ8���ֽڡ�����16+8*6=64�ֽڣ��պõ���һ�������еĳ��ȣ�
 * ��32-36�д�����ʾ��ÿ�ο�ʼ��ѭ��ʱ�����ڴ�ץȡ�����ݿ�ʵ���ϸ�����longs[i][0]��longs[i][5]��ȫ������
 * ���պ�64�ֽڣ�����ˣ���ѭ��ʱ���е����ݶ���L1����������У��������ǳ��졣

���磬��32-36�д���ע�Ͷ���25-29�д�����棬��ô������ɴ����Ļ���ʧЧ��
��Ϊÿ�δ��ڴ�ץȡ�Ķ���ͬ�в�ͬ�е����ݿ飨��longs[i][0]��longs[i][5]��ȫ�����ݣ���
��ѭ����һ����Ŀ�꣬ȴ��ͬ�в�ͬ�У���longs[0][0]��һ����longs[1][0]��
�����longs[0][1]-longs[0][5]�޷��ظ����ã�������ʱ��Ĳ������ͼ����λ��΢��(us)��
*/
