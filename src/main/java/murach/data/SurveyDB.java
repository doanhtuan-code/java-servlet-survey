package murach.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import murach.business.Survey;

public class SurveyDB {

    public static void insert(Survey survey) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.persist(survey);
            trans.commit();
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu Survey vào CSDL: " + e.getMessage());
            if (trans.isActive()) {
                trans.rollback();
            }
        } finally {
            em.close();
        }
    }
}
