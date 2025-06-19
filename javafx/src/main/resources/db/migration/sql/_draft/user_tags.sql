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
    CONSTRAINT fk_tag_group_tag FOREIGN KEY (tag_id) REFERENCES user_tags(tag_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_tag_group_group FOREIGN KEY (tag_group_id) REFERENCES user_tag_groups(tag_group_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE user_tag_group_hierarchy_map (
    tag_group_id INT PRIMARY KEY,
    parent_group_id INT NOT NULL,
    CONSTRAINT fk_tag_group_tag_group_group_id FOREIGN KEY (tag_group_id) REFERENCES user_tag_groups(tag_group_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_tag_group_tag_group_parent FOREIGN KEY (parent_group_id) REFERENCES user_tag_groups(tag_group_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE works_user_tags_map (
    work_id BIGINT NOT NULL,
    tag_id INT NOT NULL,
    CONSTRAINT pk_work_user_tag PRIMARY KEY (work_id, tag_id),
    CONSTRAINT fk_works_tags_work FOREIGN KEY (work_id) REFERENCES works(work_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_works_tags_user_tag FOREIGN KEY (tag_id) REFERENCES user_tags(tag_id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
