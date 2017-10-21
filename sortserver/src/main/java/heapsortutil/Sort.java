package heapsortutil;

/**
 * Created by zgl on 17-10-20.
 */
public class Sort {
//    static float[] data={
//            1111.01f,22002.01f,123f,456f,34545f,4758f,8763f,2341f,8888f,9737f
//    };
    float[] data=null;

    public float[] getData() {
        return data;
    }

    public void setData(float[] data) {
        this.data = data;
    }

    public void builHeap(float [] data){
        int length = data.length;
        for(int i=length/2-1;i>=0;i--){
             sortHeap(i,length,data);
        }

        for (int j=length-1;j>0;j--){
            swap(0,j,data);
            sortHeap(0,j,data);
        }
    }

    private  void sortHeap(int i,int len,float[] data){
        int left = 2*i+1;
        int right = 2*i+2;
        int min = 0;
        if(left<len&&data[left]<data[i]){
            min=left;
        }else {
            min=i;
        }
        if(right<len && data[right]<data[min]){
            min=right;
        }
        if(min !=i){
            swap(min,i,data);
            sortHeap(min,len,data);
        }
    }
    private  void swap(int m,int n,float []data){
        float temp;
        temp=data[m];
        data[m]=data[n];
        data[n]=temp;
    }
//
//    public static void main(String[] args) {
//        float[] data={
//                1111.01f,22002.01f,123f,456f,34545f,4758f,8763f,2341f,8888f,9737f
//        };
//        Sort sort = new Sort();
//        sort.builHeap(data);
//        for (int i=0;i<10;i++){
//            System.out.println(data[i]);
//        }
//
//
//    }
}
