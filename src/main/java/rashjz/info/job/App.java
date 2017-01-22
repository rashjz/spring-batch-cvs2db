package rashjz.info.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class App {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("/jobs/job-report.xml");

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("helloWorldJob");

        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
            JobExecution execution = jobLauncher.run(job, jobParameters);

            System.out.println("Exit Status : " + execution.getStatus());
            if (execution.getStatus() == execution.getStatus().COMPLETED) {
                System.out.println("job completed successfully");
                //Here you can perform some other business logic like cleanup
            } else if (execution.getStatus() == execution.getStatus().FAILED) {
                System.out.println("ExamResult job failed with following exceptions ");
                List<Throwable> exceptionList = execution.getAllFailureExceptions();
                for (Throwable th : exceptionList) {
                    System.err.println("exception :" + th.getLocalizedMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Done");

    }
}
