package uj.wmii.pwj.w7.insurance;
public class InsuranceEntry {
    private final int policyID;
    private final String statecode;
    private final String county;
    private final double eqSiteLimit;
    private final double huSiteLimit;
    private final double flSiteLimit;
    private final double frSiteLimit;
    private final double tiv2011;
    private final double tiv2012;
    private final double eqSiteDeductible;
    private final double huSiteDeductible;
    private final double flSiteDeductible;
    private final double frSiteDeductible;
    private final double pointLatitude;
    private final double pointLongitude;
    private final String line;
    private final String construction;
    private final int pointGranularity;

    public InsuranceEntry(String csvLine) {
        String[] parts = csvLine.split(",");
        this.policyID = Integer.parseInt(parts[0]);
        this.statecode = parts[1];
        this.county = parts[2];
        this.eqSiteLimit = Double.parseDouble(parts[3]);
        this.huSiteLimit = Double.parseDouble(parts[4]);
        this.flSiteLimit = Double.parseDouble(parts[5]);
        this.frSiteLimit = Double.parseDouble(parts[6]);
        this.tiv2011 = Double.parseDouble(parts[7]);
        this.tiv2012 = Double.parseDouble(parts[8]);
        this.eqSiteDeductible = Double.parseDouble(parts[9]);
        this.huSiteDeductible = Double.parseDouble(parts[10]);
        this.flSiteDeductible = Double.parseDouble(parts[11]);
        this.frSiteDeductible = Double.parseDouble(parts[12]);
        this.pointLatitude = Double.parseDouble(parts[13]);
        this.pointLongitude = Double.parseDouble(parts[14]);
        this.line = parts[15];
        this.construction = parts[16];
        this.pointGranularity = Integer.parseInt(parts[17]);
    }

    public String getCounty() {
        return county;
    }

    public double getTiv2011() {
        return tiv2011;
    }

    public double getTiv2012() {
        return tiv2012;
    }

    public double getValueIncrease() {
        return tiv2012 - tiv2011;
    }
}

