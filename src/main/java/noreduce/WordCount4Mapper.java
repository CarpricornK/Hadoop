package noreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCount4Mapper extends Mapper<LongWritable, Text, Text, LongWritable> {

    @Override
    public void map(LongWritable key, Text value, Context context)
           throws IOException, InterruptedException {

        String line = value.toString();

        for (String word : line.split("\\W+")) {

            if(word.length() > 0) {


                context.write(new Text(word), new LongWritable(1));
            }
        }
    }
}
