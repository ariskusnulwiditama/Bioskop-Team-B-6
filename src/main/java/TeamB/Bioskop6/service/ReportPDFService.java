package TeamB.Bioskop6.service;

import net.sf.jasperreports.engine.JasperPrint;

public interface ReportPDFService {
    public JasperPrint generateJasperPrint() throws Exception;
}
