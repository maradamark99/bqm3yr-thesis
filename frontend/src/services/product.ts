import { API_URL } from "../config";

export function getProducts(): Promise<Response> {
    return fetch(`${API_URL}/products`);
}