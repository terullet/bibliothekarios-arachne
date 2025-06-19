CREATE TABLE paragraphs (
    paragraph_id BIGINT PRIMARY KEY,
    order_number BIGINT NOT NULL,
    episode_id BIGINT NOT NULL,
    CONSTRAINT uq_paragraph_episode UNIQUE (paragraph_id, episode_id),
    CONSTRAINT uq_paragraph_order UNIQUE (episode_id, order_number),
    CONSTRAINT fk_paragraph_episode FOREIGN KEY (episode_id) REFERENCES episodes(episode_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE paragraph_updates (
    paragraph_id BIGINT NOT NULL,
    episode_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    content CLOB NOT NULL,
    CONSTRAINT pk_paragraph_update PRIMARY KEY (paragraph_id, post_id),
    CONSTRAINT fk_paragraph_update_episode_post FOREIGN KEY (post_id, episode_id) REFERENCES episode_posts(post_id, episode_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_paragraph_update_paragraph FOREIGN KEY (paragraph_id, episode_id) REFERENCES paragraphs(paragraph_id, episode_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE paragraph_deletes (
    paragraph_id BIGINT PRIMARY KEY,
    episode_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    CONSTRAINT fk_paragraph_delete_episode_post FOREIGN KEY (episode_id, post_id) REFERENCES episode_posts(post_id, episode_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_paragraph_delete_paragraph FOREIGN KEY (paragraph_id, episode_id) REFERENCES paragraphs(paragraph_id, episode_id) ON UPDATE RESTRICT ON DELETE CASCADE
);
