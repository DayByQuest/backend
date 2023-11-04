package daybyquest.image.domain;

public interface ImageIdentifierGenerator {

    String generate(final String category, final String originalName);
}
