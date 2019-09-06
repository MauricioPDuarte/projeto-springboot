package curso.springboot.util;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Component
public class RelatorioUtil {
	
	@Autowired
	private DataSource data;
	
	public byte[] gerarRelatorioPdf(String nomeRelatorio, Map<String, Object> params, ServletContext context) throws JRException, SQLException {
		
		String relatorio = "/relatorios/" + nomeRelatorio + ".jasper";

			JasperReport relatorioJasper =  (JasperReport) JRLoader.loadObjectFromFile(
					context.getRealPath(relatorio));
			
			JasperPrint printJasper = JasperFillManager.fillReport(relatorioJasper, params, data.getConnection());
			return JasperExportManager.exportReportToPdf(printJasper);
			
		
	}
}
