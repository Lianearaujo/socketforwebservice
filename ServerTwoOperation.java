import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTwoOperation {

    public static float raiz(float x){
        return (float) Math.sqrt(x);
    }
    public static float porcentagem(float x, float y){
        return x*(y/100);
    }
    public static float expone(float x, float y){
        return (float) Math.pow (x, y);
    }

    public static void main(String[] args){
        try {
            ServerSocket s = new ServerSocket(8083);
            String str;
            float result=0;
            while (true) {
                Socket c = s.accept();
                InputStream i = c.getInputStream();
                OutputStream o = c.getOutputStream();
                do {
                    byte[] line = new byte[100];
                    i.read(line);
                    str = new String(line);
                   
                    String str_opera = str.trim();
                    System.out.print(str_opera.length());
                    int position = 0;
                    String variavel1 = "";
                    String variavel2 = "";
                    for (int j=0; j <str_opera.length(); j++){
                        char value = str_opera.charAt(j);
                        if (position==0){
                            if (value == '^' || value == '$'){
                                position = j; 
                            }else {
                                variavel1 += value;
                            }
                        } else{
                            variavel2 += value;
                        }                           
                    }
                    if (position== 0){
                        result = raiz(Float.parseFloat(variavel1));
                    }else if (str_opera.charAt(position)== '$'){
                        result = porcentagem(Float.parseFloat(variavel1), Float.parseFloat(variavel2));
                    }else if (str_opera.charAt(position)=='^'){
                        result= expone(Float.parseFloat(variavel1), Float.parseFloat(variavel2));
                    }else {
                        String result_error = "Erro no operador";
                        o.write(String.valueOf(result_error).getBytes());
                    }
                    o.write(String.valueOf(result).getBytes());
                    str = new String(line);
                } while ( !str.trim().equals("bye") );
                c.close();
            }
        }
        catch (Exception err){
            System.err.println(err);
        }
    }

}