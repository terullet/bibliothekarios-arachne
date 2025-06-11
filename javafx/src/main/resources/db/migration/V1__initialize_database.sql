CREATE TABLE sites (
    site_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    site_name VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE hostnames (
    host_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hostname VARCHAR(2048) UNIQUE NOT NULL,
    site_id BIGINT NOT NULL,
    description VARCHAR(256) NOT NULL,
    CONSTRAINT fk_hostname_site FOREIGN KEY site_id REFERENCES sites(site_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE universes (
    universe_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    summary CLOB NOT NULL
);

CREATE TABLE works (
    work_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    site_id BIGINT NOT NULL,
    summary CLOB NOT NULL,
    registered_at TIMESTAMP(0) WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT uq_work_site UNIQUE (work_id, site_id),
    CONSTRAINT fk_work_site FOREIGN KEY site_id REFERENCES sites(site_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE works_universe_map (
    work_id BIGINT PRIMARY KEY,
    universe_id BIGINT NOT NULL,
    CONSTRAINT fk_works_universe_work FOREIGN KEY work_id REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_works_universe_universe FOREIGN KEY universe_id REFERENCES universes(universe_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE work_title_updates (
    title_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_id BIGINT NOT NULL,
    title VARCHAR(256) NOT NULL,
    updated_at TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    CONSTRAINT uq_work_update UNIQUE (work_id, updated_at),
    CONSTRAINT fk_title_update_work FOREIGN KEY work_id REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE narou_works (
    work_id BIGINT PRIMARY KEY,
    ncode VARCHAR(12) UNIQUE NOT NULL,
    narou_id BIGINT UNIQUE NOT NULL,
    genre_id INT NOT NULL,
    CONSTRAINT fk_narou_work FOREIGN KEY work_id REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE episodes (
    episode_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_id BIGINT NOT NULL,
    title VARCHAR(256) NOT NULL,
    order_number BIGINT NOT NULL,
    CONSTRAINT uq_episode_order UNIQUE (work_id, order_number),
    CONSTRAINT fk_episode_work FOREIGN KEY work_id REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE episode_posts (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    episode_id BIGINT NOT NULL,
    posted_at TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    CONSTRAINT uq_episode_post UNIQUE (episode_id, posted_at),
    CONSTRAINT fk_post_episode FOREIGN KEY episode_id REFERENCES episodes(episode_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE episode_deletes (
    episode_id BIGINT PRIMARY KEY,
    deleted_at TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_delete_episode FOREIGN KEY episode_id REFERENCES episodes(episode_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE paragraphs (
    paragraph_id BIGINT PRIMARY KEY,
    order_number BIGINT NOT NULL,
    episode_id BIGINT NOT NULL,
    CONSTRAINT uq_paragraph_episode UNIQUE (paragraph_id, episode_id),
    CONSTRAINT uq_paragraph_order UNIQUE (episode_id, order_number),
    CONSTRAINT fk_paragraph_episode FOREIGN KEY episode_id REFERENCES episodes(episode_id)
);

CREATE TABLE paragraph_updates (
    update_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paragraph_id BIGINT NOT NULL,
    episode_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    content CLOB NOT NULL,
    CONSTRAINT uq_paragraph_episode_post UNIQUE (paragraph_id, post_id),
    CONSTRAINT fk_paragraph_update_episode_post FOREIGN KEY (post_id, episode_id) REFERENCES episode_posts(post_id, episode_id),
    CONSTRAINT fk_paragraph_update_paragraph FOREIGN KEY (paragraph_id, episode_id) REFERENCES paragraphs(paragraph_id, episode_id)
);

CREATE TABLE paragraph_deletes (
    paragraph_id BIGINT PRIMARY KEY,
    deleted_at TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_delete_paragraph FOREIGN KEY paragraph_id REFERENCES paragraphs(paragraph_id)
);

CREATE TABLE contributors (
    contributor_id BIGINT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE contributors_sites_map (
    contributor_id BIGINT NOT NULL,
    site_id BIGINT NOT NULL,
    handle_name VARCHAR(256) NOT NULL,
    in_site_id VARCHAR(256) NOT NULL,
    CONSTRAINT pk_contributor_site PRIMARY KEY (contributor_id, site_id),
    CONSTRAINT uq_site_in_site UNIQUE (site_id, in_site_id),
    CONSTRAINT fk_contributors_sites_contributor FOREIGN KEY contributor_id REFERENCES contributors(contributor_id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_contributors_sites_site FOREIGN KEY site_id REFERENCES sites(site_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE contribution_types (
    type_id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE contributions (
    contribution_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_id BIGINT NOT NULL,
    contributor_id BIGINT NOT NULL,
    site_id BIGINT NOT NULL,
    type_id INT NOT NULL,
    CONSTRAINT fk_contribution_work_site FOREIGN KEY (work_id, site_id) REFERENCES works(work_id, site_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_contribution_contributor_site FOREIGN KEY (contributor_id, site_id) REFERENCES contributors_sites(contributor_id, site_id) ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_contribution_contribution_type FOREIGN KEY type_id REFERENCES contribution_types(type_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE user_tags (
    tag_id INT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(256) NOT NULL
);

CREATE TABLE user_tag_groups (
    tag_group_id INT AUTO_INCREMENT PRIMARY KEY,
    tag_group_name VARCHAR(256) NOT NULL
);

CREATE TABLE user_tag_tag_group_map (
    tag_id INT PRIMARY KEY,
    tag_group_id INT NOT NULL,
    CONSTRAINT fk_tag_group_tag FOREIGN KEY tag_id REFERENCES user_tags(tag_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_tag_group_group FOREIGN KEY tag_group_id REFERENCES user_tag_groups(tag_group_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE user_tag_group_tag_group_map (
    tag_group_id INT PRIMARY KEY,
    parent_group_id INT NOT NULL,
    CONSTRAINT fk_tag_group_tag_group_group_id FOREIGN KEY tag_group_id REFERENCES user_tag_groups(tag_group_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_tag_group_tag_group_parent FOREIGN KEY parent_group_id REFERENCES user_tag_groups(tag_group_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE works_user_tags_map (
    work_id BIGINT NOT NULL,
    tag_id INT NOT NULL,
    CONSTRAINT pk_work_user_tag PRIMARY KEY (work_id, tag_id),
    CONSTRAINT fk_works_tags_work FOREIGN KEY work_id REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_works_tags_user_tag FOREIGN KEY tag_id REFERENCES user_tags(tag_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE review_target_types (
    type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE reviews (
    review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    target_type INT NOT NULL,
    registered_at TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_review_target_type FOREIGN KEY target_type REFERENCES review_target_types(type_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE comments (
    review_id BIGINT PRIMARY KEY,
    comment CLOB NOT NULL,
    CONSTRAINT fk_comment_review FOREIGN KEY review_id REFERENCES reviews(review_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE ratings (
    review_id BIGINT PRIMARY KEY,
    rate FLOAT NOT NULL,
    CONSTRAINT fk_rating_review FOREIGN KEY review_id REFERENCES reviews(review_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE review_work_map (
    review_id BIGINT PRIMARY KEY,
    work_id BIGINT NOT NULL,
    CONSTRAINT fk_reviews_works_review FOREIGN KEY review_id REFERENCES reviews(review_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_reviews_works_work FOREIGN KEY work_id REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE review_episodes_map (
    review_id BIGINT NOT NULL,
    episode_id BIGINT NOT NULL,
    CONSTRAINT pk_reviews_episodes PRIMARY KEY (review_id, episode_id),
    CONSTRAINT fk_reviews_episodes_review FOREIGN KEY review_id REFERENCES reviews(review_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_reviews_episodes_episode FOREIGN KEY episode_id REFERENCES episodes(episode_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE bookmarkers (
    bookmarker_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bookmarker_name VARCHAR(256) NOT NULL
);

CREATE TABLE bookmarker_paragraph_map (
    bookmarker_id BIGINT PRIMARY KEY,
    paragraph_id BIGINT NOT NULL,
    CONSTRAINT fk_bookmarker_paragraphs_bookmarker FOREIGN KEY bookmarker_id REFERENCES bookmarkers(bookmarker_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_bookmarker_paragraphs_paragraph FOREIGN KEY paragraph_id REFERENCES paragraphs(paragraph_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE reading_records (
    record_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    read_seconds INT NOT NULL,
    read_from TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
    read_to TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE reading_record_paragraphs_map (
    record_id BIGINT NOT NULL,
    paragraph_id BIGINT NOT NULL,
    CONSTRAINT pk_reading_record_paragraph PRIMARY KEY (record_id, paragraph_id),
    CONSTRAINT fk_reading_record_paragraphs_record FOREIGN KEY record_id REFERENCES reading_records(record_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_reading_record_paragraphs_paragraph FOREIGN KEY paragraph_id REFERENCES paragraphs(paragraph_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
