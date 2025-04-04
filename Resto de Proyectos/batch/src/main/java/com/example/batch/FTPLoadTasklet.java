package com.example.batch;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.repository.JobRepository;

public class FTPLoadTasklet implements Tasklet, InitializingBean {


    private static final Logger log = LoggerFactory.getLogger(FTPLoadTasklet.class);
    private Resource source;

    public void setDirectoryResource(Resource directory) {
        this.source = directory;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(source, "directory must be set");
    }

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        File dir = source.getFile();
        Assert.state(dir.isDirectory(), "No es un Directory");
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File file, String name) {
                return name.toLowerCase().endsWith(".csv");
            }
        });
        // if(files.length % 2 == 1) throw new UnexpectedJobExecutionException("Error
        // forzado");
        for (int i = 0; i < files.length; i++) {
            Files.copy(files[i].toPath(), Paths.get("input/" + files[i].getName()),
                    StandardCopyOption.REPLACE_EXISTING);
            log.info("Copy " + files[i].getName());
        }
        return RepeatStatus.FINISHED;
    }

    

}
