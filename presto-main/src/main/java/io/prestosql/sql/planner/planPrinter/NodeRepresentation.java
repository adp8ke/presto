/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.sql.planner.planPrinter;

import io.prestosql.cost.PlanNodeCostEstimate;
import io.prestosql.cost.PlanNodeStatsEstimate;
import io.prestosql.sql.planner.Symbol;
import io.prestosql.sql.planner.plan.PlanNodeId;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class NodeRepresentation
{
    private final PlanNodeId id;
    private final String type;
    private final String identifier;
    private final List<Symbol> outputs;
    private final List<PlanNodeId> children;
    private final Optional<PlanNodeStats> stats;
    private final List<PlanNodeStatsEstimate> estimatedStats;
    private final List<PlanNodeCostEstimate> estimatedCost;

    private final StringBuilder details = new StringBuilder();

    public NodeRepresentation(
            PlanNodeId id,
            String type,
            String identifier,
            List<Symbol> outputs,
            Optional<PlanNodeStats> stats,
            List<PlanNodeStatsEstimate> estimatedStats,
            List<PlanNodeCostEstimate> estimatedCost,
            List<PlanNodeId> children)
    {
        this.id = requireNonNull(id, "id is null");
        this.type = requireNonNull(type, "name is null");
        this.identifier = requireNonNull(identifier, "identifier is null");
        this.outputs = requireNonNull(outputs, "outputs is null");
        this.stats = requireNonNull(stats, "stats is null");
        this.estimatedStats = requireNonNull(estimatedStats, "estimatedStats is null");
        this.estimatedCost = requireNonNull(estimatedCost, "estimatedCost is null");
        this.children = requireNonNull(children, "children is null");

        checkArgument(estimatedCost.size() == estimatedStats.size(), "size of cost and stats list does not match");
    }

    public void appendDetails(String string, Object... args)
    {
        if (args.length == 0) {
            details.append(string);
        }
        else {
            details.append(format(string, args));
        }
    }

    public void appendDetailsLine(String string, Object... args)
    {
        appendDetails(string, args);
        details.append('\n');
    }

    public PlanNodeId getId()
    {
        return id;
    }

    public String getType()
    {
        return type;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public List<Symbol> getOutputs()
    {
        return outputs;
    }

    public List<PlanNodeId> getChildren()
    {
        return children;
    }

    public String getDetails()
    {
        return details.toString();
    }

    public Optional<PlanNodeStats> getStats()
    {
        return stats;
    }

    public List<PlanNodeStatsEstimate> getEstimatedStats()
    {
        return estimatedStats;
    }

    public List<PlanNodeCostEstimate> getEstimatedCost()
    {
        return estimatedCost;
    }
}
