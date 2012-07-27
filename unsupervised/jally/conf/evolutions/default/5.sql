# --- Sample dataset

# --- !Ups

alter table iteration add column total_stories integer;

# --- !Downs

alter table iteration drop column total_stories;


