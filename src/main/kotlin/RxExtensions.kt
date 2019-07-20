import io.reactivex.Observable

fun <A, B> Observable<List<A>>.mapList(
    mappingFunction: (A) -> B
): Observable<List<B>> =
    map { iterable -> iterable.map(mappingFunction) }