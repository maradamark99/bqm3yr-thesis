import { Category } from "../types";
import { useEffect, useState } from "react";

const API_URL = import.meta.env.VITE_API_URL;

export function useCategories() {
	const [categories, setCategories] = useState<Category[][]>([[]]);
    useEffect(() => {
        fetch(API_URL + "/api/v1/categories")
            .then((res) => res.json())
            .then((data) => {
                setCategories([data]);
            });
    }, []);

    return {
        categories,
        setCategories,
    };
}

export async function useSubCategories(categoryId: number): Promise<Category[]> {
    const params = new URLSearchParams();
    params.append("parentId", categoryId.toString());
    const res = await fetch(API_URL + `/api/v1/categories?${params.toString()}`);
    return await res.json();
}