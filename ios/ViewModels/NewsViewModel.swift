import Foundation
import Combine

class NewsViewModel: ObservableObject {
    @Published var articles: [Article] = []
    @Published var isLoading: Bool = false
    @Published var errorMessage: String? = nil
    
    private let newsService = NewsService()
    private var cancellables = Set<AnyCancellable>()
    
    func fetchArticles() {
        isLoading = true
        errorMessage = nil
        
        newsService.getArticles()
            .receive(on: DispatchQueue.main)
            .sink(receiveCompletion: { [weak self] completion in
                self?.isLoading = false
                if case .failure(let error) = completion {
                    self?.errorMessage = error.localizedDescription
                }
            }, receiveValue: { [weak self] articles in
                self?.articles = articles
            })
            .store(in: &cancellables)
    }
}
