package daybyquest.relation.domain;


import org.springframework.data.repository.Repository;

public interface BlockRepository extends Repository<Block, BlockId> {

    Block save(Block block);

}
