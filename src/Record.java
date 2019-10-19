import com.csvreader.CsvWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Record {

    String[] record = new String[6];
    long time1 = System.currentTimeMillis();

    public void setStart(int board,Player player1,Player player2){
        Date t = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        record[0] = df.format(t);

        record[2] = board+"*"+board;
        record[3] = (player1.getColor() == 1) ? player1.getName():player2.getName();
        record[4] = (player2.getColor() == -1)? player2.getName():player1.getName();

    }

    public void writeCsv(){
        String path = "src/Reversi.csv";
        File f = new File(path);

        try{
            long time2 = System.currentTimeMillis();
            record[1] = String.valueOf((time2 - time1)/1000);

            BufferedWriter writer = new BufferedWriter(new FileWriter(f,true));
            CsvWriter csvWriter = new CsvWriter(writer,',');
            csvWriter.writeRecord(record);
            csvWriter.close();

            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
