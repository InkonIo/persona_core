package kz.persona.core.repository.mongo;

import kz.persona.core.model.entity.mongo.ProfileDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileDocumentRepository extends MongoRepository<ProfileDocument, String> {
}
