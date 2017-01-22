package rashjz.info.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;
import rashjz.info.model.Report;

import java.text.SimpleDateFormat;

public class ReportFieldSetMapper implements FieldSetMapper<Report> {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public Report mapFieldSet(FieldSet fieldSet) throws BindException {
		System.out.println("mapFieldSet"+fieldSet.readInt(0)+" "+fieldSet.readString(4));
		Report report = new Report();
		report.setId(fieldSet.readInt(0));
		report.setName(fieldSet.readString(1));
		report.setSurname(fieldSet.readString(2));
		report.setTransaction(fieldSet.readBigDecimal(3));
		//default format yyyy-MM-dd
//		 report.setDate(fieldSet.readDate(4));
		String date = fieldSet.readString(4);
		try {
			report.setDate(dateFormat.parse(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return report;
		
	}

}