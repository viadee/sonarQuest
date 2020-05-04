
export interface Condition {
    id: number,
    metricKey: string,
    actualValue: number,
    errorThreshold: number,
    comparator: string,
    status: any,
    title: string
}
