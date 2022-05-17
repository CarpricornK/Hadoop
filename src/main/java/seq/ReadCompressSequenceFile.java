package seq;

import lombok.extern.log4j.Log4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.compress.SnappyCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

@Log4j
public class ReadCompressSequenceFile extends Configuration implements Tool {

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            log.info("시퀸스 파일로 압축된 파일과 압축해제될 파일을 입력해야 합니다.");
            System.exit(-1);
        }
        int exitCode = ToolRunner.run(new ReadCompressSequenceFile(), args);

        System.exit(exitCode);
    }

    @Override
    public void setConf(Configuration configuration) {
        configuration.set("AppName","Compress SequencesFile Read Test");
    }

    @Override
    public Configuration getConf() {

        Configuration conf = new Configuration();

        this.setConf(conf);

        return conf;
    }

    @Override
    public int run(String[] args) throws Exception {

        Configuration conf = this.getConf();
        String appName = conf.get("AppName");

        log.info("appName : " + appName);

        Job job = Job.getInstance(conf);

        job.setJarByClass(ReadCompressSequenceFile.class);

        job.setJobName(appName);

        FileInputFormat.setInputPaths(job, new Path(args[0]));

        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setInputFormatClass(SequenceFileInputFormat.class);

        job.setNumReduceTasks(0);

        boolean success = job.waitForCompletion(true);

        return (success ? 0 : 1);

    }







}
