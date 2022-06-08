import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ProcessReader {

    public static void main(String[] args) {
        try {
            String line;
            OutputStream stdin = null;
            InputStream stderr = null;
            InputStream stdout = null;

            // launch the command and grab stdin/stdout and stderr
            Process process = Runtime.getRuntime().exec("python ./test_stdin.py");
            stdin = process.getOutputStream();
            stderr = process.getErrorStream();
            stdout = process.getInputStream();

            BufferedReader brCleanUp = new BufferedReader(new InputStreamReader(stdout));
            line = "la la la\n";   
            stdin.write(line.getBytes());
            stdin.flush();

            
            line = brCleanUp.readLine();
            System.out.println("Read from `test_stdin.py`: " + line);
      
            // You could write to sdtin too but it's useless for the ls we are doing ;)


            line = brCleanUp.readLine();
            System.out.println("Read from `test_stdin.py`: " + line);
            line = brCleanUp.readLine();
            System.out.println("Read from `test_stdin.py`: " + line);
            line = "bla bla bla\n";
            stdin.write(line.getBytes());
            stdin.flush();
            line = brCleanUp.readLine();
            System.out.println("Read from `test_stdin.py`: " + line);
            brCleanUp.close();

            // clean up if any output in stderr
            brCleanUp = 
            new BufferedReader (new InputStreamReader (stderr));
            while ((line = brCleanUp.readLine ()) != null) {
                System.out.println ("[Stderr] " + line);
            }
            brCleanUp.close();
            System.out.println("Exit value: " + process.exitValue());
      
            process.destroy();
          } catch (Exception err) {
            err.printStackTrace();
          }
    }
}