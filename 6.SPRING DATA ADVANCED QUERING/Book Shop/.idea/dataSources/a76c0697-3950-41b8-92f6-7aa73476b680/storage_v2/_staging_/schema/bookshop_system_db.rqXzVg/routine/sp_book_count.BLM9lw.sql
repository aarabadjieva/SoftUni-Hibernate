create procedure sp_book_count(IN author_first_name varchar(50), IN author_last_name varchar(50), OUT books_count int)
begin
  set books_count = (select count(b.id)from books as b join
                           authors as a on b.author_id = a.id
  where a.first_name = author_first_name
    and a.last_name = author_last_name
  group by b.author_id);
end;

