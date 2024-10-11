package ma.yc;

import ma.yc.entity.JobOffer;
import ma.yc.service.JobOfferService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.time.LocalDateTime;

public class Main {
    public static void main ( String[] args ) {

//        EntityManager entityManager = EntityManagerProvider.getInstance().getEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();

        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {
            JobOfferService jobOfferService = container.select(JobOfferService.class).get();

            JobOffer jobOffer = new JobOffer();
            jobOffer.setTitle("q");
            jobOffer.setDescription("q");
            jobOffer.setActive(true);
            jobOffer.setDateEnd(LocalDateTime.now());
            jobOffer.setDatePublish(LocalDateTime.now());
            jobOffer.setRequiredSkills("fghj,qdqs,qdss");

            jobOfferService.create(jobOffer);

            System.out.println("here : " + jobOfferService.delete(jobOfferService.findById(402)));


        }
    }
}
