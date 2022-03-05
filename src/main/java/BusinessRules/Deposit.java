package BusinessRules;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Deposit {
    private int id;
    private double amount;
    private double percent;
    private double pretermPercent;
    private int termDays;
    private Date startDate;
    private boolean withPercentCapitalization;
    private Client client;
    private static final double PERCENTAGE_AMOUNT = 12; //ежегодная капитализация


    public Deposit(int id, double amount, double percent, double pretermPercent, int termDays, Date startDate, boolean withPercentCapitalization, Client client) {
        this.id = id;
        this.amount = amount;
        this.percent = percent;
        this.pretermPercent = pretermPercent;
        this.termDays = termDays;
        this.startDate = startDate;
        this.withPercentCapitalization = withPercentCapitalization;
        this.client = client;
    }

    public Deposit(int id, double amount, double percent, double pretermPercent, int termDays, Date startDate, boolean withPercentCapitalization) {
        this.id = id;
        this.amount = amount;
        this.percent = percent;
        this.pretermPercent = pretermPercent;
        this.termDays = termDays;
        this.startDate = startDate;
        this.withPercentCapitalization = withPercentCapitalization;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public double getPretermPercent() {
        return pretermPercent;
    }

    public void setPretermPercent(double pretermPercent) {
        this.pretermPercent = pretermPercent;
    }

    public int getTermDays() {
        return termDays;
    }

    public void setTermDays(int termDays) {
        this.termDays = termDays;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public boolean isWithPercentCapitalization() {
        return withPercentCapitalization;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setWithPercentCapitalization(boolean withPercentCapitalization) {
        this.withPercentCapitalization = withPercentCapitalization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deposit)) return false;
        Deposit deposit = (Deposit) o;
        return id == deposit.id &&
                Double.compare(deposit.amount, amount) == 0 &&
                Double.compare(deposit.percent, percent) == 0 &&
                Double.compare(deposit.pretermPercent, pretermPercent) == 0 &&
                termDays == deposit.termDays &&
                withPercentCapitalization == deposit.withPercentCapitalization &&
                startDate.equals(deposit.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, percent, pretermPercent, termDays, startDate, withPercentCapitalization);
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "id=" + id +
                ", amount=" + amount +
                ", percent=" + percent +
                ", pretermPercent=" + pretermPercent +
                ", termDays=" + termDays +
                ", startDate=" + startDate +
                ", withPercentCapitalization=" + withPercentCapitalization +
                ", client=" + client +
                '}';
    }

    private double getTermPeriodInYears(int currentDays) {
        double result = (double) currentDays / 365;
        return result;
    }

    private int getCapitalizationsAmount(int actualTermDays) {
        int result =  actualTermDays / (int) (365 / PERCENTAGE_AMOUNT);
        return result;
    }

    public double getEarningsWithCap(Double percent, int actualTermDays) {
        double result = amount * Math.pow((1 + ((percent / 100) / PERCENTAGE_AMOUNT)), getCapitalizationsAmount(getActualTermDays(actualTermDays)));
        return result;
    }

    public double getEarningsWithoutCap(Double percent, int actualTermDays) {
        double result = (amount * percent * getTermPeriodInYears(getActualTermDays(actualTermDays))) / 100;
        return result;
    }

    private int getActualTermDays(int currentDays){
        int startDays = (int) TimeUnit.DAYS.convert(startDate.getTime(), TimeUnit.MILLISECONDS);
        int result = currentDays - startDays;
        return result;
    }


}
