export interface Purchase {
    id?: String;
    description: String;
    products?: [
        {
            id: String,
            product: {
                id: String,
                name: String,
                price: String
            },
            quantity: String
        }
    ]
}