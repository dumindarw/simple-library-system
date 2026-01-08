package org.dr.library.mappers;

import org.dr.library.dto.BookDto;
import org.dr.library.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper( BookMapper.class );

    Book bookDtoToBook(BookDto book);

    BookDto bookToBookDto(Book book);


    List<BookDto> bookListToBookDtoList(List<Book> bookList);


}
