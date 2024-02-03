export type Category = {
  id: number,
  value: string,
  parentId: number | null,
  leaf: boolean
}