package noreduce;

import lombok.extern.log4j.Log4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.reduce.LongSumReducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import tool.WordCount2Reducer;


@Log4j
public class WordCount4 extends Configuration implements Tool {

    public static void main(String[] args) throws Exception {

        if(args.length != 1) {
            log.info("분석결과가 저장될 폴더를 입력해야 합니다.");
            System.exit(-1);
        }
        int exitCode = ToolRunner.run(new WordCount4(), args);

        System.exit(exitCode);
    }

    @Override
    public void setConf(Configuration configuration) {

        configuration.set("AppName","Cache Test");
    }


    @Override
    public Configuration getConf() {
        Configuration conf = new Configuration();

        this.setConf(conf);

        return conf;
    }


    @Override
    public int run(String[] args) throws Exception {
        String analysisFile = "/comedies";

        Configuration conf = this.getConf();
        String appName = conf.get("AppName");

        log.info("appName :"+ appName);

        Job job = Job.getInstance(conf);

        job.addCacheFile(new Path(analysisFile).toUri());

        job.setJarByClass(WordCount4.class);

        job.setJobName(appName);

        FileInputFormat.setInputPaths(job, new Path(args[0]));

        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(WordCount4Mapper.class);

        job.setReducerClass(LongSumReducer.class);

        job.setOutputKeyClass(Text.class);

        job.setOutputValueClass(LongWritable.class);

        boolean success = job.waitForCompletion(true);

        return (success ? 0 : 1);


    }


}
