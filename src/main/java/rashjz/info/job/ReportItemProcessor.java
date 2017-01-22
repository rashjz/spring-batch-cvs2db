package rashjz.info.job;

import org.springframework.batch.item.ItemProcessor;
import rashjz.info.model.Report;

public class ReportItemProcessor implements ItemProcessor<Report, Report> {

	@Override
	public Report process(Report item) throws Exception {
		
		System.out.println("Processing..." + item);
		return item;
	}

}