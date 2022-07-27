package TeamB.Bioskop6.service;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

public class ReportPDFImpl implements ReportPDFService {
    private DataSource dataSource;

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JasperPrint generateJasperPrint() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/reports/reservation.jrxml");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, getConnection());
        return jasperPrint;
    }
}

