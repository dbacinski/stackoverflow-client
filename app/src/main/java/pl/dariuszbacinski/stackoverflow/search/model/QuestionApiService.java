package pl.dariuszbacinski.stackoverflow.search.model;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

interface QuestionApiService {

    @GET("search?site=stackoverflow")
    Observable<ResponseContainer<Question>> searchByTitle(@Query("intitle") String query, @Query("sort") Sort sort, @Query("order") Order order);
}
