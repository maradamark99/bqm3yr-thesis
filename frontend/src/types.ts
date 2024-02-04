export type Pageable<T> = {
  content: T[],
  pageable: {
    pageNumber: number,
    pageSize: number,
    sort: {
      sorted: boolean,
      unsorted: boolean,
      empty: boolean
    },
    offset: number,
    paged: boolean,
    unpaged: boolean
  },
  totalPages: number,
  totalElements: number,
  last: boolean,
  first: boolean,
  size: number,
  number: number,
  sort: {
    sorted: boolean,
    unsorted: boolean,
    empty: boolean
  },
  numberOfElements: number,
  empty: boolean
};
export type Product = {
    id: number,
    name: string,
    currentPrice: number,
    avgRating: number,
    ratingCount: number,
    thumbnailUrl: string
}


export type ProductView = {
    id: number;
    name: string;
    description: string;
    condition: string;
    thumbnailUrl: string;
    createdAt: string;
    currentPrice: number;
    mediaUrls: string[];
};

export type Category = {
  id: number,
  value: string,
  parentId: number | null,
  leaf: boolean
}

export type PageableOptions = {
  page: number;
  sortOption: SortOption;  
};

export type SortOption = {
  orderBy: string;
  direction?: 'asc' | 'desc';
}

export type OrderBy = {
	value: string;
	label: string;
	direction?: 'asc' | 'desc';
};
