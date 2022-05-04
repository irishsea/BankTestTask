package BusinessRules.Interfaces;

import BusinessRules.Client;
import BusinessRules.Deposit;

import java.util.Date;
import java.util.List;

public interface IDepositManager {

    Deposit addDeposit(Client client, double amount, double percent, double pretermPercent,
                       int termDays, Date startDate, boolean withPercentCapitalization);

    List<Deposit> getClientDeposits(Client client);

    List<Deposit> getAllDeposits();

    double getEarnings(Deposit deposit, Date currentDate);

    /**
     * Метод удаляет запись о вкладе и возвращает сумму к выплате в кассе.
     * Если вклад закрывается досрочно, то сумма к выплате рассчитывается
     * исходя из процента при досрочном изъятии.
     */

    double removeDeposit(Deposit deposit, Date closeDate);
}
